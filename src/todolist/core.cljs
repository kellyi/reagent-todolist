(ns todolist.core
    (:require
      [reagent.core :as r]))

;; -------------------------
;; Views


(defn hello-world
  []
  [:p "hello world!"])

(defn ul-of-numbers-from-range
  [range-of-numbers]
  [:ul
   (map (fn [n]
          [:li n])
        range-of-numbers)])

(defn home-page []
  [:div
   [:h2 "Welcome to Reagent"]
   (ul-of-numbers-from-range (range 1 10))])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
