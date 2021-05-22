(ns cljs-fulcro-pwa.ui
  (:require
   [cljs-fulcro-pwa.mutations :as api]
   [com.fulcrologic.fulcro.components :as c :refer [defsc]]
   [com.fulcrologic.fulcro.dom :as d]))

(defsc Person [_ {:person/keys [id name age]}
               {:keys [onDelete]}]
  {:query [:person/id :person/name :person/age]
   :ident :person/id}
  (d/li
   (d/h5 (str name " (age: " age ")"))
   (d/button {:onClick #(onDelete id)} "X")))

(def ui-person (c/computed-factory Person {:keyfn :person/id}))

(defsc Screen [this {:screen/keys [id label people]}]
  {:query [:screen/id :screen/label {:screen/people (c/get-query Person)}]
   :ident :screen/id}
  (let [delete-person
        (fn [person-id]
          (c/transact!
           this
           [(api/delete-person {:screen/id id :person/id person-id})]))]
    (d/div
     (d/h4 label)
     (d/ul
      (map #(ui-person % {:onDelete delete-person}) people)))))

(def ui-screen (c/factory Screen))

(defsc Container [this {:container/keys [content]}]
  {:query [:container/id {:container/content (c/get-query Screen)}]
   :ident :container/id}
  (let [next-screen
        (fn [] (c/transact!
                this [(api/next-screen {:container/content content})]))]
    (d/div
     (ui-screen content)
     (d/button {:onClick next-screen} "->"))))

(def ui-container (c/factory Container))

(defsc Root [_ {{:keys [:main-container]} :container/id}]
  {:query [{:container/id [:container/id
                           {:main-container (c/get-query Container)}]}]}
  (d/div (ui-container main-container)))
