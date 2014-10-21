#ifndef WIKIGENERATOR_H
#define WIKIGENERATOR_H

#include "xsltconverter.h"

class WikiGenerator : public XsltConverter
{
public:
    WikiGenerator();
    virtual ~WikiGenerator();
    bool convert();
};

#endif // WIKIGENERATOR_H
