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

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoord;
layout (location = 2) in vec3 aNormal;
layout (location = 3) in float textureSelect;

out vec2 TexCoord;
out vec3 Normal;
out float textureForFragment;
out vec3 FragPos;
out vec4[16] FragPosLightSpace;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

uniform PointLight pointLights[16];
uniform int pointLightCount;

uniform DirectionalLight directionalLights[8];
uniform int directionalLightCount;

uniform SpotLight spotLights[8];
uniform int spotLightCount;

uniform bool shadow;

void main()
{
    gl_Position = projection * view * model * vec4(aPos, 1.0);
    TexCoord = aTexCoord;
    textureForFragment = textureSelect;
    Normal = mat3(transpose(inverse(model))) * aNormal;
    FragPos = vec3(model * vec4(aPos, 1.0));
    
    if (!shadow) {
    	for (int i = 0; i < directionalLightCount; i++) {
			FragPosLightSpace[i] = directionalLights[i].lightSpaceMatrix * vec4(FragPos, 1.0);
		}
		for (int i = 0; i < spotLightCount; i++) {
			FragPosLightSpace[i] = spotLights[i + 8].lightSpaceMatrix * vec4(FragPos, 1.0);
		}
	}
}
	
}