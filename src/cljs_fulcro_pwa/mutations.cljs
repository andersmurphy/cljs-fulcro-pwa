(ns cljs-fulcro-pwa.mutations
  (:require [com.fulcrologic.fulcro.mutations :as m :refer [defmutation]])
  (:require [com.fulcrologic.fulcro.algorithms.merge :as merge]))

(defmutation delete-person
  "Mutation: Delete the person with `name` from the list with `list-name`"
  [{list-id :list/id person-id :person/id}]
  (action [{:keys [state]}]
          (swap! state merge/remove-ident*
                 [:person/id person-id]
                 [:list/id list-id :list/people])))
