(ns cljs-fulcro-pwa.database)

(defn table-ns [table]
  (-> table ffirst key namespace))

(defn table-id-key [table]
  (if-let [table-ns (table-ns table)]
    (keyword (str table-ns "/id"))
    :id))

(defn by-id [table]
  (->> (map (juxt(table-id-key table) identity) table)
       (into {})))

(defn build-db [tables]
  (->> (map (juxt table-id-key by-id) tables)
       (into {})))

(def person-table
  #{{:person/id 1 :person/name "Sally" :person/age 32}
    {:person/id 2 :person/name "Joe"   :person/age 22}
    {:person/id 3 :person/name "Fred"  :person/age 11}
    {:person/id 4 :person/name "Bobby" :person/age 55}})

(def srceen-table
  #{{:screen/id :login
     :screen/type :login
     :screen/label "Login"}
    {:screen/id :question-1
     :screen/type :question
     :screen/label "Questions about love"}
    {:screen/id :question-2
     :screen/type :question
     :screen/label "Questions about love"}
    {:screen/id :question-3
     :screen/type :question
     :screen/label "Questions about love"}
    {:screen/id :pick-card-1
     :screen/type :pick-card}
    {:screen/id :your-card-1
     :screen/type :your-card}
    {:screen/id :pick-card-2
     :screen/type :pick-card}
    {:screen/id :your-card-2
     :screen/type :your-card}
    {:screen/id :pick-card-3
     :screen/type :pick-card}
    {:screen/id :your-card-3
     :screen/type :your-card}
    {:screen/id :reading
     :screen/type :reading}})

(def container-table
  #{{:container/id :main-container
     :container/content [:screen/id :login]}})

(def database
  (build-db [person-table
             srceen-table
             container-table]))
