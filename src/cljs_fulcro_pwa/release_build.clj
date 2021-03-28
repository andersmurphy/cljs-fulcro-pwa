(ns cljs-fulcro-pwa.release-build)

(def release-build? false)

(defn hook
  {:shadow.build/stages #{:compile-prepare}}
  [build-state release-build]
  (alter-var-root #'release-build? (constantly release-build))
  build-state)

(defmacro when-release-build [& body]
  (let [rb# release-build?]
    `(when ~rb# ~@body)))

(defmacro when-not-release-build [& body]
  (let [rb# release-build?]
    `(when-not ~rb# ~@body)))
