package com.openclassrooms.mynews.Models.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by berenger on 13/03/2018.
 */

public class Response {

        @SerializedName("docs")
        @Expose
        private final List<Doc> docs = null;

        public List<Doc> getDocs() {
            return docs;
        }

        public class Doc {

            @SerializedName("web_url")
            @Expose
            private String webUrl;
            @SerializedName("headline")
            @Expose
            private Headline headline;
            @SerializedName("pub_date")
            @Expose
            private String pubDate;
            @SerializedName("news_desK")
            @Expose
            private String newsDesK;
            @SerializedName("section_name")
            @Expose
            private String sectionName;
            @SerializedName("subsection_name")
            @Expose
            private String subsectionName;
            @SerializedName("multimedia")
            @Expose
            private final List<Multimedium> multimedia = null;

            public String getWebUrl() {
                return webUrl;
            }

            public Headline getHeadline() {
                return headline;
            }

            public String getPubDate() {
                return pubDate;
            }

            public String getNewsDesK() {
                return newsDesK;
            }

            public String getSectionName() {
                return sectionName;
            }

            public String getSubsectionName() {
                return subsectionName;
            }

            public List<Multimedium> getMultimedia() {
                return multimedia;
            }

            public class Headline {

                @SerializedName("main")
                @Expose
                private String main;

                public String getMain() {
                    return main;
                }
            }

            public class Multimedium {

                @SerializedName("url")
                @Expose
                private String url;

                public String getUrl() {
                    return url;
                }

            }
        }
}
