(ns cljs-fulcro-pwa.ui
  (:require
   [cljs-fulcro-pwa.mutations :as api]
   [cljs-fulcro-pwa.release-build :refer [when-release-build]]
   [com.fulcrologic.fulcro.components :as c :refer [defsc]]
   [com.fulcrologic.fulcro-css.localized-dom :as d]
   [com.fulcrologic.fulcro-css.css-injection :as inj]))

(defsc Person [_ {:person/keys [id name age] :as props}
               {:keys [onDelete]}]
  {:query [:person/id :person/name :person/age]
   :ident (fn [] [:person/id (:person/id props)])
   :css [[:.red {:color       "red"}]]}
  (d/li
   (d/h5 :.red (str name " (age: " age ")"))
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
   (inj/style-element
    (dissoc {:component Root
             :react-key (str (rand-int 100000))}
            ;; only reload css when developing
            (when-release-build :react-key)))
   (d/div :.parent
          (ui-person-list friends)
          (ui-person-list enemies))))
