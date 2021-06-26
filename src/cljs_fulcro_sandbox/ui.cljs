(ns cljs-fulcro-sandbox.ui
  (:require
   [cljs-fulcro-sandbox.mutations :as api]
   [com.fulcrologic.fulcro.components :as c :refer [defsc]]
   [com.fulcrologic.fulcro.mutations :as m]
   [com.fulcrologic.fulcro.dom.events :as evt]
   [com.fulcrologic.fulcro.dom :as d]))

(defsc Question [this {:question/keys [name answer]}]
  {:query [:question/id :question/name :question/answer]
   :ident :question/id}
  (js/console.log this)
  (d/div (d/h2 name)
         (d/input
          {:value answer
           :type "text"
           :onChange
           (fn [evt]
             (m/set-value! this :question/answer (evt/target-value evt)))})))

(def ui-question (c/factory Question))

(defsc Choice [this {:choice/keys [name options]}]
  {:query [:choice/id :choice/name :choice/options]
   :ident :choice/id}
  (d/div
   (d/h2 name)
   (map
    (fn [{:keys [text value]}]
      (js/console.log text)
      (d/button
       {:value value
        :key value
        :onClick
        (fn [evt]
          (m/set-value! this :choice/selected (evt/target-value evt)))}
       text))
    options)))

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
  (d/div {:style {:display "grid"
                  :placeItems "center"}}
         (ui-screen current-screen)))
