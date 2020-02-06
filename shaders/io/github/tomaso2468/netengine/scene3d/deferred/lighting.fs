#version 330

struct PointLight {
	vec3 position;
	
	vec4 ambient;
	vec4 diffuse;
	vec4 specular;
	
	float attenuation;
};

struct DirectionalLight {
	vec3 direction;
	
	vec4 ambient;
	vec4 diffuse;
	vec4 specular;
	
	mat4 lightSpaceMatrix;
};

struct SpotLight {
	vec3 position;
	vec3 direction;
	
	vec4 ambient;
	vec4 diffuse;
	vec4 specular;
	
	float attenuation;
	float cutoff;
	float cutoff2;
	
	mat4 lightSpaceMatrix;
};

uniform sampler2D position;
uniform sampler2D normal;
uniform sampler2D diffuse;
uniform sampler2D specular;
uniform sampler2D screenSpacePosition;

in vec2 texCoord;

out vec4 FragColor;

uniform PointLight pointLights[16];
uniform int pointLightCount;

uniform DirectionalLight directionalLights[8];
uniform int directionalLightCount;

uniform SpotLight spotLights[8];
uniform int spotLightCount;

uniform vec3 viewPos;

float sqrDist(vec3 a, vec3 b) {
	return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y) + (a.z - b.z) * (a.z - b.z);
}

vec3 calculateAmbientLight(vec3 color, vec3 position) {
	vec3 ambient = vec3(0);
	
	for (int i = 0; i < spotLightCount; i++) {
		float atten = 1 / (1 + sqrDist(spotLights[i].position, position) * spotLights[i].attenuation);
		
		ambient += spotLights[i].ambient.rgb;
	}
	for (int i = 0; i < directionalLightCount; i++) {
		ambient += directionalLights[i].ambient.rgb;
	}
	for (int i = 0; i < pointLightCount; i++) {
		float atten = 1 / (1 + sqrDist(pointLights[i].position, position) * pointLights[i].attenuation);
		
		ambient += pointLights[i].ambient.rgb * atten;
	}
	return ambient * color;
}

vec3 calculateDiffuseLight(vec3 color, vec3 position, vec3 normal) {
	vec3 diffuse = vec3(0);
	
	vec3 norm = normalize(normal);
	
	for (int i = 0; i < spotLightCount; i++) {
		vec3 lightDir = normalize(spotLights[i].position - position);
		float theta = dot(lightDir, normalize(-spotLights[i].direction));
		
		if (theta > spotLights[i].cutoff2) {
			float atten = 1 / (1 + sqrDist(spotLights[i].position, position) * spotLights[i].attenuation);
			
			float diff = max(dot(norm, lightDir), 0.0);
			
			float epsilon   = spotLights[i].cutoff - spotLights[i].cutoff2;
			float intensity = clamp((theta - spotLights[i].cutoff2) / epsilon, 0.0, 1.0);
			
    		diffuse += vec3(spotLights[i].diffuse) * diff * atten * intensity;
		}
	}
	for (int i = 0; i < directionalLightCount; i++) {
		vec3 lightDir = normalize(-directionalLights[i].direction);
		float diff = max(dot(norm, lightDir), 0.0);
    	diffuse += vec3(directionalLights[i].diffuse) * diff;
	}
	for (int i = 0; i < pointLightCount; i++) {
		float atten = 1 / (1 + sqrDist(pointLights[i].position, position) * pointLights[i].attenuation);
	
		vec3 lightDir = normalize(pointLights[i].position - position);
		float diff = max(dot(norm, lightDir), 0.0);
    	diffuse += vec3(pointLights[i].diffuse) * diff * atten;
	}
	return diffuse * color.rgb;
}

vec3 calculateSpecularLight(vec3 color, vec3 position, vec3 normal, float strength) {
	vec3 specular = vec3(0);
	
	vec3 norm = normalize(normal);
	
	for (int i = 0; i < spotLightCount; i++) {
		vec3 lightDir = normalize(spotLights[i].position - position);
		float theta = dot(lightDir, normalize(-spotLights[i].direction)); 
		
		if (theta > spotLights[i].cutoff2) {
			float atten = 1 / (1 + sqrDist(spotLights[i].position, position) * spotLights[i].attenuation);
			
			float epsilon   = spotLights[i].cutoff - spotLights[i].cutoff2;
			float intensity = clamp((theta - spotLights[i].cutoff2) / epsilon, 0.0, 1.0);
			
			vec3 lightDir   = normalize(spotLights[i].position - position);
			vec3 viewDir    = normalize(viewPos - position);
			vec3 halfwayDir = normalize(lightDir + viewDir);
		
			float spec = pow(max(dot(norm, halfwayDir), 0.0), strength);
			specular += spec * vec3(spotLights[i].specular) * intensity;
		}
	}
	for (int i = 0; i < directionalLightCount; i++) {
		vec3 lightDir = normalize(-directionalLights[i].direction);
		
		vec3 viewDir    = normalize(viewPos - position);
		vec3 halfwayDir = normalize(lightDir + viewDir);
		
		float spec = pow(max(dot(norm, halfwayDir), 0.0), strength);
		specular += spec * vec3(directionalLights[i].specular);
	}
	for (int i = 0; i < pointLightCount; i++) {
		float atten = 1 / (1 + sqrDist(pointLights[i].position, position) * pointLights[i].attenuation);
		
		vec3 lightDir   = normalize(pointLights[i].position - position);
		vec3 viewDir    = normalize(viewPos - position);
		vec3 halfwayDir = normalize(lightDir + viewDir);
		
		float spec = pow(max(dot(norm, halfwayDir), 0.0), strength);
		specular += spec * vec3(pointLights[i].specular) * atten;
	}
	return specular * color;
}

void main()
{
	vec3 fragPosition = texture(position, texCoord).rgb;
	vec3 fragNormal = texture(normal, texCoord).rgb;
	vec4 fragDiffuse = texture(diffuse, texCoord);
	vec3 fragSpecular = texture(specular, texCoord).rgb;
	vec3 fragSSP = texture(screenSpacePosition, texCoord).rgb;
	
	FragColor = vec4(calculateDiffuseLight(fragDiffuse.rgb, fragPosition, fragNormal)
	+ calculateSpecularLight(fragSpecular.rgb, fragPosition, fragNormal, 256)
	+ calculateAmbientLight(fragDiffuse.rgb, fragPosition)
	+ fragDiffuse.rgb / 3, fragDiffuse.a);
	
	gl_FragDepth = fragSSP.z;
}