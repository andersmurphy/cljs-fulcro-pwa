(ns cljs-fulcro-pwa.mutations
  (:require [com.fulcrologic.fulcro.mutations :as m :refer [defmutation]]
            [com.fulcrologic.fulcro.algorithms.merge :as merge]))

(defmutation delete-person
  [{list-id :list/id person-id :person/id}]
  (action [{:keys [state]}]
          (swap! state merge/remove-ident*
                 [:person/id person-id]
                 [:list/id list-id :list/people])))

(defmutation next-person-list
  [{{id :list/id} :container/content}]
  (action [{:keys [state]}]
          (let [next-list-id (if (= id :friends) :enemies :friends)]
            (swap! state assoc-in
                   [:container/id :main-container :container/content]
                   [:list/id next-list-id]))))
