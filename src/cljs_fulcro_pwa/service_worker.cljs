(ns cljs-fulcro-pwa.service-worker)

(defn register-service-worker []
  (when (aget js/navigator "serviceWorker")
    (-> js/navigator .-serviceWorker
        (.register "service-worker.js"))
    (js/console.log "[Service Worker] Registered")))
