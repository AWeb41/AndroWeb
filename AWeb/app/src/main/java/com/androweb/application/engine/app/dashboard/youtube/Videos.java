package com.androweb.application.engine.app.dashboard.youtube;

import com.androweb.application.R;

public class Videos {

    private String title;
    private String thumbnail_url;
    private String videoID;
	private String published;
    public Videos(String title, String thumbnail_url, String videoid, String published) {
        this.title = title;
        this.thumbnail_url= thumbnail_url;
        this.videoID = videoid;
		this.published = published;
    }

    public String getTitle() {
        return title;
    }
	
    public String getThumbnailUrl() {
        return thumbnail_url;
    }
	
    public String getVideoID() {
        return videoID;
    }
	
	public String getPublished(){
		return published;
	}
}


