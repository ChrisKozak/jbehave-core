package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.Segment;
import com.sirenian.hellbound.domain.glyph.GlyphListener;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.util.Listener;
import com.sirenian.hellbound.util.ListenerNotifier;
import com.sirenian.hellbound.util.ListenerSet;

public class Glyph {
	
	protected GlyphType type;
	protected Segment[] previousSegments;
	protected Segment[] segments;
	
	private ListenerSet listeners;
	private ListenerNotifier glyphMovementNotifier;

	public Glyph(GlyphType type, Segment[] firstPosition) {
		this.listeners = new ListenerSet();
		this.type = type;
		
		previousSegments = firstPosition;
		segments = firstPosition;
		
		glyphMovementNotifier = new ListenerNotifier() {
			public void notify(Listener listener) {
				((GlyphListener)listener).reportGlyphMovement(Glyph.this.type, previousSegments, segments);
			}
		};
	}

	public void addListener(GlyphListener listener) {
		listeners.addListener(listener);
		
		listener.reportGlyphMovement(type, previousSegments, segments);
	}

	protected void moveTo(Segment[] destination) {
		
		this.previousSegments = segments;
		this.segments = destination;
		
		listeners.notifyListeners(glyphMovementNotifier);
	}

}