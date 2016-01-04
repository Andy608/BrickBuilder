package com.bountive.graphics.render;

import com.bountive.graphics.view.AbstractCamera;

public interface IRenderer {

	void render(AbstractCamera c);
	
	void release();
}
