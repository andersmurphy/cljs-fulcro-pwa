(ns cljs-fulcro-pwa.client
  (:require
   [cljs-fulcro-pwa.ui :as ui]
   [com.fulcrologic.fulcro.application :as app]
   [com.fulcrologic.fulcro.components :as c]
   [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]))

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

(defonce app (app/fulcro-app {:initial-db database}))

(defn ^:export init
  "Shadow-cljs sets this up to be our entry-point function. See shadow-cljs.edn `:init-fn` in the modules of the main build."
  []
  (app/mount! app ui/Root "app")
  (js/console.log "Loaded"))

(defn ^:export refresh
  "During development, shadow-cljs will call this on every hot reload of source. See shadow-cljs.edn"
  []
  ;; re-mounting will cause forced UI refresh, update internals, etc.
  (app/mount! app ui/Root "app")
  ;; As of Fulcro 3.3.0, this addition will help with stale queries when using dynamic routing:
  (c/refresh-dynamic-queries! app)
  (js/console.log "Hot reload"))

(comment
  ;; current db state
  (app/current-state app)
  ;; query
  (fdn/db->tree [{:list/id [:list/id :enemies :friends]}]
                (app/current-state app)
                {}))
