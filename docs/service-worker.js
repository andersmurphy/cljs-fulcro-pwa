// Files to cache
const cacheName = "cljs-fulcro-pwa-v1"
const contentToCache =
      ["js/main.js",
       "./",
       "images/icon-192.png",
       "images/icon-512.png",
       "manifest.json"]

// Installing Service Worker
self.addEventListener("install", (e) => {
  console.log("[Service Worker] Install")
  e.waitUntil((async () => {
    const cache = await caches.open(cacheName)
    console.log("[Service Worker] Caching all: app shell and content")
    await cache.addAll(contentToCache)})())})

// Fetching content using Service Worker
self.addEventListener("fetch", (e) => {
  e.respondWith((async () => {
    const r = await caches.match(e.request);
    console.log(`[Service Worker] Fetching resource: ${e.request.url}`)
    if (r) return r
    const response = await fetch(e.request)
    const cache = await caches.open(cacheName)
    console.log(`[Service Worker] Caching new resource: ${e.request.url}`)
    cache.put(e.request, response.clone())
    return response})())})

// Deleting old caches
self.addEventListener("activate", (e) => {
  e.waitUntil((async () => {
    const keyList = await caches.keys()
    await Promise.all(keyList.map((key) => {
      if (key !== cacheName) {
        console.log(`[Service Worker] Deleting old cache: ${key}`)
        return caches.delete(key)}}))})())})
