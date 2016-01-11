#version 330 core

layout(location = 0) in vec3 position;

uniform mat4 projectionMatrix;
uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;

uniform vec3 brickColorTint;

out vec4 vColor;

vec4 mixColors(vec4 color1, vec4 color2, float lerp);

void main() {

	vec4 localPosition = vec4(position, 1.0);
	
	vColor = clamp(mixColors(localPosition, vec4(brickColorTint, 1.0), 0.8), 0.1, 1.0);
	
	
	//vColor = vec4(brickColorTint, 1.0);
	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * localPosition;
}

vec4 mixColors(vec4 color1, vec4 color2, float lerp) {
	return vec4((color1.x * (1 - lerp) + (color2.x * lerp)), 
					(color1.y * (1 - lerp) + (color2.y * lerp)), 
					(color1.z * (1 - lerp) + (color2.z * lerp)), 
					(color1.w * (1 - lerp) + (color2.w * lerp)));
}

