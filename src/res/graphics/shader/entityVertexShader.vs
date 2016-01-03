#version 330 core

layout(location = 0) in vec3 position;
//layout(location = 1) in vec4 color;

uniform mat4 projectionMatrix;
uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;

out vec4 vColor;

void main() {
	vColor = vec4(clamp(position, 0.1, 1.0), 1.0);
	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
}
