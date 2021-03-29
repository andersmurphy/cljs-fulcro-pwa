(ns cljs-fulcro-pwa.static-html-writer
  (:require [hiccup.core :refer [html]]
            [cljs-fulcro-pwa.release-build :refer [when-release-build]]))

(def index-html
  [:html {:lang "en"}
   [:head
    [:meta {:http-equiv "content-type"
            :content    "text/html; charset=UTF-8"}]
    (when-release-build
        [:meta {:http-equiv "Content-Security-Policy"
                :content
                "default-src  'none';
                 manifest-src   'self';
                 base-uri       'self';
                 form-action    'self';
                 script-src     'self';
                 img-src        'self';
                 font-src       'self';
                 style-src      'self' 'unsafe-inline'"}])
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
    [:title "CLJS PWA"]
    [:meta {:name    "description"
            :content "Clojurescript progressive web app (PWA) example"}]
    [:link {:rel "apple-touch-icon" :href "images/icon-192.png"}]
    [:link {:rel "manifest" :href "manifest.json"}]]
   [:body
    [:div {:id "app"}]
    [:script {:src "js/main.js"}]]])

(defmacro write-index-html
  "This is a macro so that it outputs the index file at compile time."
  []
  (->> (html index-html)
       (str "<!DOCTYPE html>\n")
       (spit "docs/index.html")))
