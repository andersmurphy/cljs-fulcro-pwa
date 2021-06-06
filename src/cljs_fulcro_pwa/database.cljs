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

(def question-table
  #{{:question/id 1
     :question/name "What is your name?"
     :question/answer   ""}
    {:question/id 2
     :question/name "Who is your daddy?"
     :question/answer   ""}
    {:question/id 3
     :question/name "What does he do?"
     :question/answer   ""}})

(def srceen-table
  #{{:screen/id :question-1
     :screen/content [:question/id 1]}
    {:screen/id :question-2
     :screen/content [:question/id 2]}
    {:screen/id :question-3
     :screen/content [:question/id 3]}})

(def container-table
  #{{:container/id :main-container
     :container/content [:screen/id :question-1]}})

(def database
  (build-db [container-table
             srceen-table
             question-table]))
