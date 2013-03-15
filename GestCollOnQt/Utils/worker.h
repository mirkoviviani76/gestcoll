#ifndef WORKER_H
#define WORKER_H

#include <QObject>

/**
 * @brief Implementa i metodi necessari per gestire un lavoro composto da diversi elementi ripetuti.
 *  Offre segnali per indicare il numero massimo di elementi e l'avanzamento.
 *
 */
class Worker : public QObject
{
    Q_OBJECT
public:
    /**
     * @brief costruttore
     *
     * @param parent
     */
    explicit Worker(QObject *parent = 0);
    /**
     * @brief distruttore
     *
     */
    virtual ~Worker() {}

signals:
    /**
     * @brief segnala il massimo possibile per il lavoro in corso
     *
     * @param max il massimo
     * @param label la label del lavoro
     */
    void signalProgressMaximum(int max, QString label);
    /**
     * @brief segnala l'avanzamento del lavoro
     *
     * @param val il valore
     */
    void signalProgressValue(int val);

public slots:

};

#endif // WORKER_H
