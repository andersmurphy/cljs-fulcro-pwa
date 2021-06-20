(ns cljs-fulcro-pwa.ui
  (:require
   [cljs-fulcro-pwa.mutations :as api]
   [com.fulcrologic.fulcro.components :as c :refer [defsc]]
   [com.fulcrologic.fulcro.dom :as d]))

(defsc Question [_ {:question/keys [name]}]
  {:query [:question/id :question/name]
   :ident :question/id}
  (d/div (d/h2 name)))

(def ui-question (c/factory Question))

(defsc Choice [_ {:choice/keys [name]}]
  {:query [:choice/id :choice/name]
   :ident :choice/id}
  (d/h2 name))

(def ui-choice (c/factory Choice))

(defsc Screen [this props]
  {:query (fn []
            {:question/id (c/get-query Question)
             :choice/id   (c/get-query Choice)})
   :ident (fn []
            (cond
              (:question/id props) [:question/id (:question/id props)]
              (:choice/id props)   [:choice/id   (:choice/id props)]))}
  (let [screen      (first (c/get-ident this))
        next-screen (fn [] (c/transact! this [(api/next-screen {})]))
        prev-screen (fn [] (c/transact! this [(api/prev-screen {})]))]
    (d/div
     (case screen
       :question/id (ui-question props)
       :choice/id   (ui-choice props))
     (d/button {:onClick prev-screen} "<-")
     (d/button {:onClick next-screen} "->"))))

(def ui-screen (c/factory Screen))

(defsc Root [_ {:root/keys [current-screen]}]
  {:query [{:root/current-screen (c/get-query Screen)}]}
  (d/div
   (ui-screen current-screen)))
