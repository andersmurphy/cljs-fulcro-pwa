(ns cljs-fulcro-pwa.ui
  (:require
   [com.fulcrologic.fulcro.components :as c :refer [defsc]]
   [com.fulcrologic.fulcro.dom :as d]
   [cljs-fulcro-pwa.mutations :as api]))

(defsc Person [_ {:person/keys [id name age] :as props} {:keys [onDelete]}]
  {:query [:person/id :person/name :person/age]
   :ident (fn [] [:person/id (:person/id props)])
   :initial-state
   (fn [{:keys [id name age]}] {:person/id id :person/name name :person/age age})}
  (d/li
   (d/h5 (str name " (age: " age ")"))
   (d/button {:onClick #(onDelete id)} "X")))

(def ui-person (c/computed-factory Person {:keyfn :person/id}))

(defsc PersonList [this {:list/keys [id label people] :as props}]
  {:query [:list/id :list/label {:list/people (c/get-query Person)}]
   :ident (fn [] [:list/id (:list/id props)])
   :initial-state
   (fn [{:keys [id label]}]
     {:list/id id
      :list/label label
      :list/people
      (if (= label "Friends")
        [(c/get-initial-state Person {:id 1 :name "Sally" :age 32})
         (c/get-initial-state Person {:id 2 :name "Joe"   :age 22})]
        [(c/get-initial-state Person {:id 3 :name "Fred"  :age 11})
         (c/get-initial-state Person {:id 4 :name "Bobby" :age 55})])})}
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

(defsc Root [_ {:keys [friends enemies]}]
  {:query [{:friends (c/get-query PersonList)}
           {:enemies (c/get-query PersonList)}]
   :initial-state
   (fn [_] {:friends (c/get-initial-state PersonList
                                          {:id :friends :label "Friends"})
            :enemies (c/get-initial-state PersonList
                                          {:id :enemies :label "Enemies"})})}
  (d/div
   (ui-person-list friends)
   (ui-person-list enemies)))
