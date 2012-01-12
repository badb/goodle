package edu.goodle.prototype.client;

import com.google.gwt.user.client.ui.Panel;

public abstract class GoodlePanel {
        private final GoodleServiceAsync goodleService;
        private final Goodle goodle;

        public GoodlePanel(GoodleServiceAsync goodleService, Goodle goodle) {
                this.goodle = goodle;
                this.goodleService = goodleService;
        }

        public Goodle getGoodle() {
                return goodle;
        }

        public GoodleServiceAsync getGoodleService() {
                return goodleService;
        }
}
