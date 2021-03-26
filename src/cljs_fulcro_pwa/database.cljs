(ns cljs-fulcro-pwa.database)

(def person-table
  {1 {:person/id 1 :person/name "Sally" :person/age 32}
   2 {:person/id 2 :person/name "Joe"   :person/age 22}
   3 {:person/id 3 :person/name "Fred"  :person/age 11}
   4 {:person/id 4 :person/name "Bobby" :person/age 55}})

(def list-table
  {:friends {:list/id :friends
             :list/label "Friends"
             :list/people [[:person/id 1] [:person/id 2]]}
   :enemies {:list/id :enemies
             :list/label "Enemies"
             :list/people [[:person/id 3] [:person/id 4]]}})

(def database
  {:person/id person-table
   :list/id   list-table})
