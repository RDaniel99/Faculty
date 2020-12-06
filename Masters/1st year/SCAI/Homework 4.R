library(olsrr)

df <- read.table("http://profs.info.uaic.ro/~cgatu/csia/res/house.dat", header=T)

names <- names(df)[2:14]

for (i in c(1:13)){
    best_cp <- -1
    best_adj_r_squared <- 0
    best_r_squared <- 0
    best_rss <- -1
    for (comb in combn(names, i, simplify=FALSE)){
        measurevar <- do.call(cbind, df["PRICE"])
        groupvars <- do.call(cbind, df[comb])

        formula <- measurevar ~ groupvars

        full_model <- lm(measurevar ~ ., data=df)
        model <- lm(formula, data=df)
        if (summary(model)$r.squared>best_r_squared){
            best_r_squared <- summary(model)$r.squared
            sol_r_squared <- comb
        }
        if (summary(model)$adj.r.squared>best_adj_r_squared){
            best_adj_r_squared <- summary(model)$adj.r.squared
            sol_adj_r_squared <- comb
        }
        if (abs(ols_mallows_cp(model, full_model)-i-1)<abs(best_cp - i - 1) || best_cp == -1){
            best_cp <- abs(ols_mallows_cp(model, full_model))
            sol_cp <- comb
        }
        if (sum(resid(model)^2)<best_rss || best_rss == -1){
            best_rss <- sum(resid(model)^2)
            sol_rss <- comb
        }
    }
    print(i)
    print(best_r_squared)
    print(sol_r_squared)
    print(best_adj_r_squared)
    print(sol_adj_r_squared)
    print(best_cp)
    print(sol_cp)
    print(best_rss)
    print(sol_rss)
}
