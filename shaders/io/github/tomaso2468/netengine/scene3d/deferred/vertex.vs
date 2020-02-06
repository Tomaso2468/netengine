#version 330
layout (location = 0) in vec3 vertex;
layout (location = 1) in vec2 texCoord;
layout (location = 2) in vec3 normal;
layout (location = 3) in float textureSelect;


out VS_OUT
{
    vec2 texCoord;
    vec3 normal;
    float texSelect;
    vec3 fragPos;
} vsOut;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main()
{
    gl_Position = projection * view * model * vec4(vertex, 1.0);
    
    vsOut.texCoord = texCoord;
    vsOut.texSelect = textureSelect;
    vsOut.normal = mat3(transpose(inverse(model))) * normal;
    vsOut.fragPos = vec3(model * vec4(vertex, 1.0));
}