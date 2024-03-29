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

out vec4 FragColor;

in vec2 TexCoord;
in vec3 Normal;
in float textureForFragment;
in vec3 FragPos;
in vec4[16] FragPosLightSpace;

uniform sampler2D objectTexture;
uniform sampler2D ambientLight;
uniform sampler2D diffuseLight;
uniform sampler2D specularLight;
uniform sampler2D shinyLight;

uniform PointLight pointLights[16];
uniform int pointLightCount;

uniform DirectionalLight directionalLights[8];
uniform int directionalLightCount;

uniform SpotLight spotLights[8];
uniform int spotLightCount;

uniform vec3 viewPos;

uniform bool srgbTextures;
uniform float gamma;
uniform bool srgbOutput;

uniform bool shadow;
uniform bool depth;

uniform int debugLightPos;

float sqrDist(vec3 a, vec3 b) {
	return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y) + (a.z - b.z) * (a.z - b.z);
}

vec3 calculateAmbientLight() {
	vec3 ambient = vec3(0);
	
	for (int i = 0; i < spotLightCount; i++) {
		float atten = 1 / (1 + sqrDist(spotLights[i].position, FragPos) * spotLights[i].attenuation);
		
		ambient += spotLights[i].ambient.rgb;
	}
	for (int i = 0; i < directionalLightCount; i++) {
		ambient += directionalLights[i].ambient.rgb;
	}
	for (int i = 0; i < pointLightCount; i++) {
		float atten = 1 / (1 + sqrDist(pointLights[i].position, FragPos) * pointLights[i].attenuation);
		
		ambient += pointLights[i].ambient.rgb * atten;
	}
	return ambient * texture(ambientLight, TexCoord).rgb;
}

vec3 calculateDiffuseLight() {
	vec3 diffuse = vec3(0);
	
	vec3 norm = normalize(Normal);
	
	for (int i = 0; i < spotLightCount; i++) {
		vec3 lightDir = normalize(spotLights[i].position - FragPos);
		float theta = dot(lightDir, normalize(-spotLights[i].direction));
		
		if (theta > spotLights[i].cutoff2) {
			float atten = 1 / (1 + sqrDist(spotLights[i].position, FragPos) * spotLights[i].attenuation);
			
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
		float atten = 1 / (1 + sqrDist(pointLights[i].position, FragPos) * pointLights[i].attenuation);
	
		vec3 lightDir = normalize(pointLights[i].position - FragPos);
		float diff = max(dot(norm, lightDir), 0.0);
    	diffuse += vec3(pointLights[i].diffuse) * diff * atten;
	}
	return diffuse * texture(diffuseLight, TexCoord).rgb;
}

vec3 calculateSpecularLight() {
	vec3 specular = vec3(0);
	
	vec3 norm = normalize(Normal);
	
	for (int i = 0; i < spotLightCount; i++) {
		vec3 lightDir = normalize(spotLights[i].position - FragPos);
		float theta = dot(lightDir, normalize(-spotLights[i].direction)); 
		
		if (theta > spotLights[i].cutoff2) {
			float atten = 1 / (1 + sqrDist(spotLights[i].position, FragPos) * spotLights[i].attenuation);
			
			float epsilon   = spotLights[i].cutoff - spotLights[i].cutoff2;
			float intensity = clamp((theta - spotLights[i].cutoff2) / epsilon, 0.0, 1.0);
			
			vec3 lightDir   = normalize(spotLights[i].position - FragPos);
			vec3 viewDir    = normalize(viewPos - FragPos);
			vec3 halfwayDir = normalize(lightDir + viewDir);
		
			float spec = pow(max(dot(Normal, halfwayDir), 0.0), texture(shinyLight, TexCoord).r * 512);
			specular += spec * vec3(spotLights[i].specular) * intensity;
		}
	}
	for (int i = 0; i < directionalLightCount; i++) {
		vec3 lightDir = normalize(-directionalLights[i].direction);
		
		vec3 viewDir    = normalize(viewPos - FragPos);
		vec3 halfwayDir = normalize(lightDir + viewDir);
		
		float spec = pow(max(dot(Normal, halfwayDir), 0.0), texture(shinyLight, TexCoord).r * 512);
		specular += spec * vec3(directionalLights[i].specular);
	}
	for (int i = 0; i < pointLightCount; i++) {
		float atten = 1 / (1 + sqrDist(pointLights[i].position, FragPos) * pointLights[i].attenuation);
		
		vec3 lightDir   = normalize(pointLights[i].position - FragPos);
		vec3 viewDir    = normalize(viewPos - FragPos);
		vec3 halfwayDir = normalize(lightDir + viewDir);
		
		float spec = pow(max(dot(Normal, halfwayDir), 0.0), texture(shinyLight, TexCoord).r * 512);
		specular += spec * vec3(pointLights[i].specular) * atten;
	}
	return specular * texture(specularLight, TexCoord).rgb;
}

void main()
{
	vec4 textureColor = texture(objectTexture, TexCoord);
	
	if (!shadow) {
		vec4 objectColor;
		if (srgbTextures) {
			objectColor = vec4(pow(textureColor.rgb, vec3(gamma)), textureColor.a);
		} else {
			objectColor = textureColor;
		}
	    
	    vec3 ambient = calculateAmbientLight();
	    vec3 diffuse = calculateDiffuseLight();
	    vec3 specular = calculateSpecularLight();
	    
	    FragColor = vec4(ambient + diffuse + specular, 1) * objectColor;
	    
	    if (srgbOutput) {
	    	FragColor = vec4(pow(FragColor.rgb, vec3(1.0 / gamma)), FragColor.a);
	    }
	} else {
		FragColor = textureColor;
	}
	
	if (FragColor.a < 0.05) {
		discard;
	}
	
	if (depth) {
		float dv = pow(gl_FragCoord.z, 50);
		FragColor = vec4(vec3(dv), 1);
	}
	
	if (debugLightPos > 0) {
		FragColor = FragPosLightSpace[debugLightPos + 1];
	}
} 