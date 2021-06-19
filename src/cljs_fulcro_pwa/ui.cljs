(ns cljs-fulcro-pwa.ui
  (:require
   [cljs-fulcro-pwa.mutations :as api]
   [com.fulcrologic.fulcro.components :as c :refer [defsc]]
   [com.fulcrologic.fulcro.dom :as d]))

(defsc Question [_ {:question/keys [name]}]
  {:query [:question/id :question/name]
   :ident :question/id}
  (d/h5 name))

(def ui-question (c/factory Question))

(defsc Choice [_ {:choice/keys [name]}]
  {:query [:choice/id :choice/name]
   :ident :choice/id}
  (d/h5 name))

(def ui-choice (c/factory Choice))

(defsc Container [this {:container/keys [content]}]
  {:query [:container/id
           {:container/content
            {:question/id (c/get-query Question)
             :choice/id   (c/get-query Choice)}}]
   :ident :container/id}
  (let [[content-ident id] (first content)
        next-screen
        (fn [] (c/transact!
                this [(api/next-screen
                       {:current-screen [content-ident id]})]))]
    (d/div
     (case content-ident
       :question/id (ui-question content)
       :choice/id   (ui-choice content))
     (d/button {:onClick next-screen} "->"))))

(def ui-container (c/factory Container))

(defsc Root [_ {{:keys [:main-container]} :container/id}]
  {:query [{:container/id [:container/id
                           {:main-container (c/get-query Container)}]}]}
  (d/div (ui-container main-container)))
