#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec4 color;
layout(location = 2) in vec3 normal;
layout(location = 3) in vec2 texCoords;

uniform mat4 projectionMatrix;
uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;

uniform vec3 brickColorTint;

out vec4 vColor;

vec4 mixColors(vec4 color1, vec4 color2, float lerp);

void main() {

	vec4 localPosition = vec4(position, 1.0);
	vec4 worldPosition = transformationMatrix * localPosition;
	
	gl_Position = projectionMatrix * viewMatrix * worldPosition;
	
	vec4 dramaticPosition = vec4(localPosition.x / 18, localPosition.y / 18, localPosition.z / 18, 1.0);
	vColor = clamp(mixColors(localPosition, color, 0.0), 0.3, 0.9);
	//vColor = color;
}

vec4 mixColors(vec4 color1, vec4 color2, float lerp) {
	return vec4((color1.x * (1 - lerp) + (color2.x * lerp)), 
					(color1.y * (1 - lerp) + (color2.y * lerp)), 
					(color1.z * (1 - lerp) + (color2.z * lerp)), 
					(color1.w * (1 - lerp) + (color2.w * lerp)));
}

