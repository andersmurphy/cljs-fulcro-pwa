(ns cljs-fulcro-pwa.service-worker)

(def cache-name "cljs-fulcro-pwa-v1")

(def content-to-cache ["js/main.js"
                       "./"
                       "images/icon-192.png"
                       "images/icon-512.png"
                       "manifest.json"])

(defn register-service-worker []
  (when (aget js/navigator "serviceWorker")
    (-> js/navigator .-serviceWorker
        (.register "sw.js"))
    (js/console.log "[Service Worker] Registered")))

(defn install-service-worker []
  (js/console.log "[Service Worker] Installed")
  (-> (.open js/caches cache-name)
      (.then (fn [cache]
               (js/console.log "[Service Worker] Caching all: app shell and content")
               (.addAll cache content-to-cache)))))

(defn cache-resp [req]
  (fn [resp]
    (-> (.open js/caches cache-name)
        (.then (fn [cache]
                 (js/console.log (str "[Service Worker] Caching new resource: "
                                      (.-url req)))
                 (.put cache req (.clone resp))
                 resp)))))

(defn fetch-and-cache [req]
  (-> (js/fetch req)
      (.then (cache-resp req))))

(defn get-from-cache-or-fetch [req]
  (fn [resp]
    (js/console.log (str "[Service Worker] Fetching resource: "
                         (.-url req)))
    (or resp (fetch-and-cache req))))

(defn fetch-content [e]
  (let [req (.-request e)]
    (-> (.match js/caches req)
        (.then (get-from-cache-or-fetch req)))))

(defn purge-old-caches []
  (-> (.keys js/caches)
      (.then
       (fn [ks]
         (-> (map #(when-not (= cache-name %)
                     (js/console.log (str "[Service Worker] Purging old Cache: " %))
                     (.delete js/caches %))
                  ks)
             clj->js
             js/Promise.all)))))

(defn ^:export init
  "Shadow-cljs sets this up to be our entry-point function. See shadow-cljs.edn `:init-fn` in the modules of the main build."
  []
  (.addEventListener js/self "install"  #(.waitUntil   % (install-service-worker)))
  (.addEventListener js/self "fetch"    #(.respondWith % (fetch-content %)))
  (.addEventListener js/self "activate" #(.waitUntil   % (purge-old-caches))))
