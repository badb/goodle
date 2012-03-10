package edu.goodle.prototype.client;

public abstract class GoodlePanel {
        private final GoodleServiceController controller;
        private final Goodle goodle;

        public GoodlePanel(GoodleServiceController controller, Goodle goodle) {
                this.goodle = goodle;
                this.controller = controller;
        }

        public Goodle getGoodle() {
                return goodle;
        }

        public GoodleServiceController getGoodleServiceController() {
                return controller;
        }
}
