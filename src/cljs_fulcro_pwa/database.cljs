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

(def user-table
  #{{:user/id 1}})

(def reading-table
  #{{:reading/id 1}})

(def question-table
  #{{:question/id 1
     :question/name "What is your name?"
     :question/answer :user/name}
    {:question/id 2
     :question/name "What star were you born under?"
     :question/answer :user/star-sign}})

(def choice-table
  #{{:choice/id 1
     :choice/name "What kind of reading?"
     :choice/answer :reading/kind}})

(def database
  (build-db {:root/current-screen [:question/id 1]}
            [user-table
             reading-table
             question-table
             choice-table]))
