(ns cljs-fulcro-pwa.app
  (:require
   [cljs-fulcro-pwa.ui :as ui]
   [cljs-fulcro-pwa.database :as db]
   [cljs-fulcro-pwa.release-build :refer [when-release-build]]
   [cljs-fulcro-pwa.static-html-writer :as html-writer]
   [cljs-fulcro-pwa.service-worker :as sw]
   [com.fulcrologic.fulcro.application :as app]
   [com.fulcrologic.fulcro.components :as c]
   [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]))

(html-writer/write-index
 [:html {:lang "en"}
  [:head
   [:meta {:http-equiv "content-type"
           :content    "text/html; charset=UTF-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1.0"}]
   [:title "CLJS PWA"]
   [:meta {:name    "description"
           :content "This is a dercription"}]
   [:link {:rel "stylesheet" :type "text/css" :href "styles.css"}]
   [:link {:rel "apple-touch-icon" :href "images/icon-192.png"}]
   [:link {:rel "manifest" :href "manifest.json"}]]
  [:body
   [:div {:id "app"}]
   [:script {:src "js/main.js"}]]])

(defonce app (app/fulcro-app {:initial-db db/database}))

(defn ^:export init []
  (when-release-build (sw/register-service-worker))
  (app/mount! app ui/Root "app"))

(defn ^:export refresh []
  (app/mount! app ui/Root "app")
  (c/refresh-dynamic-queries! app)
  (js/console.log "Hot reload"))

(comment
  ;; current db state
  (app/current-state app)
  ;; query
  (fdn/db->tree [{:list/id [:list/id :enemies :friends]}]
                (app/current-state app)
                {}))
