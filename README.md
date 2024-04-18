# Machine Learning Project: Game Recommendation System

This README documents a machine learning project aimed at building a game recommendation system based on user reviews and game statistics. The system utilises a dataset of game reviews to train and validate machine learning models for predicting game recommendations.

## Table of Contents

Machine Learning Project: Game Recommendation System.
  - Table of Contents
  - Personal Goal
  - Project Goal
  - Description
  - Outputs
  - Dataset
  - Models
  - Post-project Reflection
  - Helpful / Used Resources

## Personal Goal
My personal goals for this project were to gain an understanding of machine learning, build on 0 prior knowledge, and improve my "Java" skills using a wide variety of Object-orientated techniques. 

## Project Goal
The project aimed to create an accurate machine learning model that could predict whether a user would recommend a game, given factors such as hours played, price paid, and platform information.

## Description


## Outputs
The project Creates two pop-up windows.
- **Game Input GUI**: Allows users to input information such as hours played, price, and platform of use to determine if a user with similar information will recommend a game or not
- **Statistics Display**: Provides users with statistical insights into the calculated data, including average hours played, average price per hour, and platform distribution among recommended and not recommended games.

 **Testing and Validation**: After running the program, it outputs the accuracy of the model

## Dataset
The project uses a Large data set of Steam game reviews. It was divided into three distinct sections randomly.
- **Training** 60% of the original Data. This data is used to train the model and gain the statistics used in predictions.
- **Validation** 20% of the original Data. This data is used to check and improve the Machine Learning model.
- **Testing** 20% of the original Data. This data is used to test the prediction accuracy of the machine-learning model versus whether it is recommended or not.

The dataset was sourced from Kaggle and underwent preprocessing steps to handle missing values, convert categorical variables into a suitable format for machine learning models and remove irrelevant data.

## Models

The project utilises a machine learning model, specifically a Gaussian Naive Bayes classifier, to predict game recommendations based on the input features. The Gaussian Naive Bayes classifier is trained on the dataset to predict new instances.

****Gaussian Naive Bayes:****
- It is based on Bayes' theorem, which describes the probability of an event based on prior knowledge of conditions that might be related to the event.
- The algorithm assumes that the continuous values associated with each class follow a Gaussian (normal) distribution, which may require logging the data to normalise it.
- For each class, the continuous attribute values' mean (μ) and variance (σ²) are computed. These represent the central tendency and spread of the data within each class.
- Once the mean and variance for each class are known, the probability density of a given observation value (v) within a specific class (Cₖ) can be calculated. This is done by plugging the observation value (v) into the equation for a normal distribution with parameters μₖ and σ²ₖ.

**The probability density function for a normal distribution is given by:**

![image](https://github.com/cian-collage/oop-machine-learning/assets/124142292/73dcfc4f-40a2-45bf-8201-9ee2421c4f03)

Where:
- **p(x=v∣Cₖ** is the probability density of observing the value V given class Cₖ.
- **μₖ** is the mean of the values associated with class Cₖ.
- **μₖ^2** is the variance of the values associated with class Cₖ.
  
After the data is put through the classifier to find the probability of an instance being recommended and not recommended, the model takes the more significant probability and declares it as its prediction. This prediction is then compared to the actual result as seen in the `Testeing.csv` and `Validation.csv`, allowing for a test of accuracy.

![image](https://github.com/cian-collage/oop-machine-learning/assets/124142292/e4a91710-aaa4-48ec-8a5b-eca369a4c759)

## Post-project Reflection

Looking back on the project, I made a few mistakes. Here are a few things I would do differently:
1. I would spend more time trying to understand the basics of machine learning, such as training, validation and testing. This caused me to have to restart the code for the project as I needed to understand the end goal.
2. I would do more research into naive Bayes, as it took me a long time to understand how it works. In particular, my lack of understanding in the validation step caused many setbacks as I tried to fix the maths.
3. I would choose a more straightforward Data set for my first machine learning project. The self-introduction of continuous variables complicated much of my code in the mid to late stages of the project.
4. I would make Git commits earlier in the coding process as my laptop died mid-project, and I lost much of my progress, particularly my original cleaned data sets.

## Helpful / Used Resources
- [[Normal distribution image sourse](https://www.slideshare.net/plummer48/normal-and-non-normal-distributions)]: Image of Normal and Skewed distributions.
- [[Naive Bayes classifier wiki](https://en.wikipedia.org/wiki/Naive_Bayes_classifier#Gaussian_naive_Bayes)]: Wikipedia page on Naive Bayes classifier and its variations.
- [[info video 1](https://www.youtube.com/watch?v=H3EjCKtlVog)]: Vidio by StatQuest with Josh Starmer on Gaussian Naive Bayes, Clearly Explained.
- [[info video 2](https://www.youtube.com/watch?v=u5jRUg10bpw)]: Vidio by Intellipaat on What is Gaussian Naive Bayes Algorithm / Gaussian Naive Bayes Implementation.
- [[kaggle](https://www.kaggle.com/datasets?search=Game)]: A website where many databases can be found on an extensive range of topics.
