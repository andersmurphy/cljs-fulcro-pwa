(ns cljs-fulcro-pwa.ui
  (:require
   [com.fulcrologic.fulcro.components :as c :refer [defsc]]
   [cljs-fulcro-pwa.mutations :as api]
   [com.fulcrologic.fulcro-css.localized-dom :as d]
   [com.fulcrologic.fulcro-css.css-injection :as inj]))

(defsc Person [_ {:person/keys [id name age] :as props}
               {:keys [onDelete]}]
  {:query [:person/id :person/name :person/age]
   :ident (fn [] [:person/id (:person/id props)])
   :css [[:.bold {:font-weight "bold"}]]}
  (d/li
   (d/h5 :.bold (str name " (age: " age ")"))
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

(defsc Root [_ {{:keys [friends enemies]} :list/id}]
  {:query [{:list/id [:list/id
                      {:friends (c/get-query PersonList)}
                      {:enemies (c/get-query PersonList)}]}]
   :css [[:.parent {:display "grid"
                    :place-items "center"}]]}
  (d/div
   (inj/style-element {:component Root})
   (d/div :.parent
          (ui-person-list friends)
          (ui-person-list enemies))))
