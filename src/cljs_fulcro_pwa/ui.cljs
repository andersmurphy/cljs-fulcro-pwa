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

(defsc Container [this {:container/keys [content]}]
  {:query [:container/id {:container/content (c/get-query Question)}]
   :ident :container/id}
  (let [next-screen
        (fn [] (c/transact!
                this [(api/next-screen content)]))]
    (d/div
     (ui-question content)
     (d/button {:onClick next-screen} "->"))))

(def ui-container (c/factory Container))

(defsc Root [_ {{:keys [:main-container]} :container/id}]
  {:query [{:container/id [:container/id
                           {:main-container (c/get-query Container)}]}]}
  (d/div (ui-container main-container)))
