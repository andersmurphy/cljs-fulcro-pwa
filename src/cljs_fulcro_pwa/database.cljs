(ns cljs-fulcro-pwa.database)

(def person-table
  {1 {:person/id 1 :person/name "Sally" :person/age 32}
   2 {:person/id 2 :person/name "Joe"   :person/age 22}
   3 {:person/id 3 :person/name "Fred"  :person/age 11}
   4 {:person/id 4 :person/name "Bobby" :person/age 55}})

(def srceen-table
  {:login     {:screen/id :login
               :screen/label "Login"
               :screen/people [[:person/id 1] [:person/id 2]]}
   :question  {:screen/id :question
               :screen/label "Questions about love"
               :screen/people [[:person/id 3] [:person/id 4]]}
   :pick-card {:screen/id :pick-card
               :screen/label "Enemies"
               :screen/people [[:person/id 3] [:person/id 4]]}
   :your-card {:screen/id :your-card
               :screen/label "Enemies"
               :screen/people [[:person/id 3] [:person/id 4]]}
   :reading   {:screen/id :reading
               :screen/label "Enemies"
               :screen/people [[:person/id 3] [:person/id 4]]}})

(def container-table
  {:main-container {:container/id :main-container
                    :container/content [:screen/id :login]}})

(def database
  {:person/id    person-table
   :screen/id    srceen-table
   :container/id container-table})
