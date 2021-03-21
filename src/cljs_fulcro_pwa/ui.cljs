(ns cljs-fulcro-pwa.ui
  (:require
   [com.fulcrologic.fulcro.components :as c :refer [defsc]]
   [com.fulcrologic.fulcro.dom :as d]))

(defsc Person [_ {:person/keys [name age]}]
  {:query [:person/name :person/age]
   :initial-state
   (fn [{:keys [name age]}] {:person/name name :person/age  age})}
  (d/li
   (d/h5 (str name " (age: " age ")"))))

(def ui-person (c/factory Person {:keyfn :person/name}))

(defsc PersonList [_ {:list/keys [label people]}]
  {:query [:list/label {:list/people (c/get-query Person)}]
   :initial-state
   (fn [{:keys [label]}]
     {:list/label label
      :list/people
      (if (= label "Friends")
        [(c/get-initial-state Person {:name "Sally" :age 32})
         (c/get-initial-state Person {:name "Joe"   :age 22})]
        [(c/get-initial-state Person {:name "Fred"  :age 11})
         (c/get-initial-state Person {:name "Bobby" :age 55})])})}
  (d/div
   (d/h4 label)
   (d/ul
    (map ui-person people))))

(def ui-person-list (c/factory PersonList))

(defsc Root [_ {:keys [friends enemies]}]
  {:query [{:friends (c/get-query PersonList)}
           {:enemies (c/get-query PersonList)}]
   :initial-state
   (fn [_] {:friends (c/get-initial-state PersonList {:label "Friends"})
            :enemies (c/get-initial-state PersonList {:label "Enemies"})})}
  (d/div
   (ui-person-list friends)
   (ui-person-list enemies)))
