(ns cljs-fulcro-pwa.app
  (:require
   [cljs-fulcro-pwa.ui :as ui]
   [cljs-fulcro-pwa.database :as db]
   [cljs-fulcro-pwa.release-build :refer [when-release-build]]
   [cljs-fulcro-pwa.static-html-writer :refer [write-index-html]]
   [cljs-fulcro-pwa.service-worker :as sw]
   [com.fulcrologic.fulcro.application :as app]
   [com.fulcrologic.fulcro.components :as c]
   [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]))

;; Outputs index.html file
(write-index-html)

(defonce app (app/fulcro-app {:initial-db db/database}))

(defn ^:export init
  "Shadow-cljs sets this up to be our entry-point function. See shadow-cljs.edn `:init-fn` in the modules of the main build."
  []
  (when-release-build (sw/register-service-worker))
  (app/mount! app ui/Root "app"))

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
