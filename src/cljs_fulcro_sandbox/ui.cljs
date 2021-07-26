(ns cljs-fulcro-sandbox.ui
  (:require
   [cljs-fulcro-sandbox.mutations :as api]
   [com.fulcrologic.fulcro.components :as c :refer [defsc]]
   [com.fulcrologic.fulcro.mutations :as m]
   [com.fulcrologic.fulcro.dom.events :as evt]
   [com.fulcrologic.fulcro.dom :as d]
   [com.fulcrologic.fulcro.algorithms.react-interop :as interop]
   [react-spring :as spring]))

(def ui-animated-div (interop/react-factory spring/animated.div))

(defsc Question [this {:question/keys [name answer]}]
  {:query [:question/id :question/name :question/answer]
   :ident :question/id
   :use-hooks? true}
  (let [next-screen (fn [] (c/transact! this [(api/next-screen {})]))]
    (d/div {:style {:fontSize "3vh"
                    :padding   "32px"}}
           (ui-animated-div
            {:style
             (spring/useSpring
              (clj->js {:from {:opacity 0}
                        :to {:opacity 1}
                        :delay 200}))}
            (d/h2 name))
           (d/input
            {:style {:width "100%"
                     :padding "8px"
                     :marginTop  "16px"
                     :marginBottom  "16px"}
             :value answer
             :type "text"
             :onChange
             (fn [evt]
               (m/set-value! this :question/answer (evt/target-value evt)))})
           (d/button {:style {:width "100%"
                              :padding "8px"
                              :marginTop  "16px"
                              :marginBottom  "16px"}
                      :onClick next-screen} "Next"))))

(def ui-question (c/factory Question))

(defsc Choice [this {:choice/keys [name options]}]
  {:query [:choice/id :choice/name :choice/options]
   :ident :choice/id}
  (let [next-screen (fn [] (c/transact! this [(api/next-screen {})]))]
    (d/div
     {:style {:fontSize "3vh"
              :padding   "32px"}}
     (d/h2 name)
     (map
      (fn [{:keys [text value]}]
        (d/button
         {:style {:width "100%"
                  :padding "8px"
                  :marginTop  "16px"
                  :marginBottom  "16px"}
          :value value
          :key value
          :onClick
          (fn [evt]
            (m/set-value! this :choice/selected (evt/target-value evt))
            (next-screen))}
         text))
      options))))

(def ui-choice (c/factory Choice))

(defsc Screen [this props]
  {:query (fn []
            {:question/id (c/get-query Question)
             :choice/id   (c/get-query Choice)})
   :ident (fn []
            (cond
              (:question/id props) [:question/id (:question/id props)]
              (:choice/id props)   [:choice/id   (:choice/id props)]))}
  (let [screen      (first (c/get-ident this))]
    (d/div {:style {:flex "1"}}
           (case screen
             :question/id (ui-question props)
             :choice/id   (ui-choice props)))))

(def ui-screen (c/factory Screen))

(defsc Root [_ {:root/keys [current-screen]}]
  {:query [{:root/current-screen (c/get-query Screen)}]}
  (d/div {:style {:height          "100%"
                  :display         "flex"
                  :flexDirection   "column"
                  :backgroundColor "#191950"
                  :color           "#e1e1e1"}}
         (d/div {:style {:flex "1"}})
         (ui-screen current-screen)
         (d/div {:style {:flex "1"}})))
