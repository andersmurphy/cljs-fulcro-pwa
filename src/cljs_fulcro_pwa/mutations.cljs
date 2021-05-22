(ns cljs-fulcro-pwa.mutations
  (:require [com.fulcrologic.fulcro.mutations :as m :refer [defmutation]]
            [com.fulcrologic.fulcro.algorithms.merge :as merge]))

(defmutation delete-person
  [{screen-id :screen/id person-id :person/id}]
  (action [{:keys [state]}]
          (swap! state merge/remove-ident*
                 [:person/id person-id]
                 [:screen/id screen-id :screen/people])))

(defmutation next-screen
  [{{id :screen/id} :container/content}]
  (action [{:keys [state]}]
          (let [next-screen-id (if (= id :login) :question-1 :login)]
            (swap! state assoc-in
                   [:container/id :main-container :container/content]
                   [:screen/id next-screen-id]))))
