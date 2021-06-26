(ns cljs-fulcro-pwa.database)

(defn table-ns [table]
  (-> table ffirst key namespace))

(defn table-id-key [table]
  (if-let [table-ns (table-ns table)]
    (keyword (str table-ns "/id"))
    :id))

(defn by-id [table]
  (->> (map (juxt (table-id-key table) identity) table)
       (into {})))

(defn build-db [root-state tables]
  (->> (map (juxt table-id-key by-id) tables)
       (into root-state)))

(def question-table
  #{{:question/id 1
     :question/name "What is your name?"
     :question/answer ""}
    {:question/id 2
     :question/name "What star were you born under?"
     :question/answer ""}})

(def choice-table
  #{{:choice/id 1
     :choice/name "What kind of reading?"
     :choice/options [{:text "Love"    :value :love}
                      {:text "Wealth"  :value :wealth}
                      {:text "Career"  :value :career}
                      {:text "Compass" :value :compass}]
     :choice/selected nil}})

(def database
  (build-db {:root/current-screen [:question/id 1]}
            [question-table
             choice-table]))
