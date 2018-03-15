package com.openclassrooms.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by berenger on 13/03/2018.
 */

public class Result {

        @SerializedName("section")
        @Expose
        private String section;
        @SerializedName("subsection")
        @Expose
        private String subsection;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("published_date")
        @Expose
        private String publishedDate;

        //FOR TOPSTORIES IMG URL
        @SerializedName("multimedia")
        @Expose
        private List<Multimedium> multimedia = null;

        //FOR MOSTPOPULAR IMG URL
        @SerializedName("media")
        @Expose
        private List<Medium> media = null;

        //------------------
        // GETTERS
        //------------------

        public String getSection() {
            return section;
        }

        public String getSubsection() {
            return subsection;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public List<Medium> getMedia() {
            return media;
        }

        public List<Multimedium> getMultimedia() {
            return multimedia;
        }

        //FOR TOPSTORIES IMG URL
        public class Multimedium {
            @SerializedName("url")
            @Expose
            private String url;

            public String getUrl() {
                return url;
            }
        }

        //FOR MOSTPOPULAR IMG URL
        public class Medium {

            @SerializedName("media-metadata")
            @Expose
            private List<MediaMetadatum> mediaMetadata = null;

            public List<MediaMetadatum> getMediaMetadata() {
                return mediaMetadata;
            }

            public class MediaMetadatum {

                @SerializedName("url")
                @Expose
                private String url;

                public String getUrl() {
                    return url;
                }
            }
        }
}

