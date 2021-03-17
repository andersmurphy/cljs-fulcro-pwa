(ns cljs-fulcro-pwa.ui
  (:require
   [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
   [com.fulcrologic.fulcro.dom :as dom :refer [div p li h5 h4 ul]]))

(defsc Person [this {:person/keys [name age]}]
  (li
   (h5 (str name " (age: " age ")"))))

(def ui-person (comp/factory Person))

(defsc PersonList [this {:list/keys [label people]}]
  (div
   (h4 label)
   (ul
    (map ui-person people))))

(def ui-person-list (comp/factory PersonList))

(defsc Root [this props]
  (let [ui-data {:friends
                 {:list/label "Friends"
                  :list/people  [{:person/name "Sally" :person/age 32}
                                 {:person/name "Joe" :person/age 22}]}
                 :enemies
                 {:list/label "Enemies"
                  :list/people [{:person/name "Fred" :person/age 11}
                                {:person/name "Bobby" :person/age 55}]}}]
    (div
     (ui-person-list (:friends ui-data))
     (ui-person-list (:enemies ui-data)))))
