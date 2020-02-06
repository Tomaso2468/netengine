#version 330

layout (location = 0) out vec4 position;
layout (location = 1) out vec4 normal;
layout (location = 2) out vec4 diffuse;
layout (location = 3) out vec4 specular;
layout (location = 4) out vec4 screenPos;

in VS_OUT
{
    vec2 texCoord;
    vec3 normal;
    float texSelect;
    vec3 fragPos;
} vsOut;

uniform sampler2D diffuseLight;
uniform sampler2D specularLight;

uniform bool srgbTextures;
uniform float gamma;

void main()
{
	diffuse = texture(diffuseLight, vsOut.texCoord);
	specular = texture(specularLight, vsOut.texCoord);
	
	if (srgbTextures) {
		diffuse = vec4(pow(diffuse.rgb, vec3(gamma)), diffuse.a);
		diffuse = vec4(pow(specular.rgb, vec3(gamma)), specular.a);
	}
	
	if (diffuse.a < 0.05) {
		discard;
	}
	
	position = vec4(vsOut.fragPos, 1);
	normal = vec4(vsOut.normal, 0);
	
	screenPos = gl_FragCoord;
} 