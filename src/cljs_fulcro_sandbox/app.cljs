(ns cljs-fulcro-sandbox.app
  (:require
   [cljs-fulcro-sandbox.ui :as ui]
   [cljs-fulcro-sandbox.database :as db]
   [cljs-fulcro-sandbox.static-html-writer :as html-writer]
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
   [:title "CLJS Fulcro Sandbox"]
   [:meta {:name    "description"
           :content "This is a dercription"}]
   [:link {:rel "stylesheet" :type "text/css" :href "https://fonts.googleapis.com/css?family=Roboto"}]
   [:link {:rel "stylesheet" :type "text/css" :href "styles.css"}]

   [:link {:rel "apple-touch-icon" :href "images/icon-192.png"}]]
  [:body
   [:div {:id "app"}]
   [:script {:src "js/main.js"}]]])

(defonce app (app/fulcro-app {:initial-db db/database}))

(defn ^:export init []
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
