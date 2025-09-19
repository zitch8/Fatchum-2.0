# Fatchum 2.0
**Fatchum 2.0** is a recipe recommendation app built to help users discover delicious recipes using the ingredients they already have on hand. It's a simple, smart, and efficient way to reduce food waste and find your next favorite meal.

## Features
**Mobile App (Kotlin)**
- Search recipes by typing ingredients you have in your fridge.
- Get suggestions by food tags
- Firebase integration that supports Google Accounts

**Backend (FastAPI + ML)**
- **Count Vectorizer + Cosine Similarity** -> tag-based recommendations.
- **TF-IDF + Cosine Similarity** -> ingredient-based search engine.

## Tech Stack
**Mobile (Frontend)**
- Kotlin + Andorid Studio
- Firebase (Auth, Firestore, Cloud Storage)
- Retrofit for API calls

**Backend ([API](https://github.com/zitch8/vectorizer-api))**
- FastAPI
- Scikit-learn (TF-IDF, CountVectorizer, Cosine Similarity)
- Uvicorn / Gunicorn for deployment
- Render for server hosting
