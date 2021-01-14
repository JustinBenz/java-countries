package com.lambdaschool.javacountries.repositories;

import com.lambdaschool.javacountries.models.Country;

@FunctionalInterface
public interface CheckCountry {
    public boolean test(Country c);
}
