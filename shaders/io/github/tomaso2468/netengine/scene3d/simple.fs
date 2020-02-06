#version 330

in vec2 texCoord;

out vec4 FragColor;

uniform sampler2D aTexture;

void main()
{
    FragColor = vec4(texture(aTexture, texCoord).rgb, 1);
}