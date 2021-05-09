(ns cljs-fulcro-pwa.ui
  (:require
   [cljs-fulcro-pwa.mutations :as api]
   [com.fulcrologic.fulcro.components :as c :refer [defsc]]
   [com.fulcrologic.fulcro.dom :as d]))

(defsc Person [_ {:person/keys [id name age] :as props}
               {:keys [onDelete]}]
  {:query [:person/id :person/name :person/age]
   :ident (fn [] [:person/id (:person/id props)])}
  (d/li
   (d/h5 (str name " (age: " age ")"))
   (d/button {:onClick #(onDelete id)} "X")))

(def ui-person (c/computed-factory Person {:keyfn :person/id}))

(defsc PersonList [this {:list/keys [id label people] :as props}]
  {:query [:list/id :list/label {:list/people (c/get-query Person)}]
   :ident (fn [] [:list/id (:list/id props)])}
  (let [delete-person
        (fn [person-id]
          (c/transact!
           this
           [(api/delete-person {:list/id id :person/id person-id})]))]
    (d/div
     (d/h4 label)
     (d/ul
      (map #(ui-person % {:onDelete delete-person}) people)))))

(def ui-person-list (c/factory PersonList))

(defsc Container [this {:container/keys [content] :as props}]
  {:query [:container/id {:container/content (c/get-query PersonList)}]
   :ident (fn [] [:container/id (:container/id props)])}
  (let [next-person-list
        (fn [] (c/transact!
                this [(api/next-person-list {:container/content content})]))]
    (d/div
     (ui-person-list content)
     (d/button {:onClick next-person-list} "->"))))

(def ui-container (c/factory Container))

(defsc Root [_ {{:keys [:main-container]} :container/id}]
  {:query [{:container/id [:container/id
                           {:main-container (c/get-query Container)}]}]}
  (d/div (ui-container main-container)))
