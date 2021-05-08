(ns cljs-fulcro-pwa.static-html-writer
  (:require [hiccup.core :refer [html]]))

(defmacro write-index
  "Outputs index at compile time."
  [index-html]
  (->> (html index-html)
       (str "<!DOCTYPE html>\n")
       (spit "docs/index.html")))
