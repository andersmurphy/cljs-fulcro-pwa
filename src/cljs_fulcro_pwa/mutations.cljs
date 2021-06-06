(ns cljs-fulcro-pwa.mutations
  (:require [com.fulcrologic.fulcro.mutations :as m :refer [defmutation]]))

(def routes
  {:question-1 :question-2
   :question-2 :question-3
   :question-3 :question-1})

(defmutation next-screen
  [{{id :screen/id} :container/content}]
  (action [{:keys [state]}]
          (swap! state assoc-in
                 [:container/id :main-container :container/content]
                 [:screen/id (routes id)])))
