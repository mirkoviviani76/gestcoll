/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StatistichePanel.java
 *
 * Created on 18-feb-2011, 11.49.52
 */
package gui.extraPanels.statistiche;

import java.util.TreeMap;

import javax.swing.JPanel;

import main.GestLog;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import works.Statistiche;
import exceptions.XmlException;

/**
 * Gestisce la visualizzazione delle statistiche e dei grafici
 * 
 * @author intecs
 */
public final class StatistichePanel extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JToolBar jToolBar1;

	/** Creates new form StatistichePanel */
	public StatistichePanel() {
		initComponents();
		try {
			this.jTabbedPane1.addTab(
					"Dimensione",
					createMyBarChart("Monete per dimensione", "dim",
							"nr. monete", getDatasetCoinBySize()));
			this.jTabbedPane1.addTab(
					"Anno",
					createMyBarChart("Monete per anno", "anno", "nr. monete",
							getDatasetCoinByYear()));
			this.jTabbedPane1.addTab(
					"Metallo",
					createMyBarChart("Monete per metallo", "anno",
							"nr. monete", getDatasetCoinByMetal()));
			this.jTabbedPane1.addTab(
					"Metallo2",
					createMy3DPieChart("Monete per metallo",
							getPieDatasetCoinByMetal()));
			this.jTabbedPane1.addTab(
					"Nominale",
					createMy3DPieChart("Monete per nominale",
							getPieDatasetCoinByNominal()));
		} catch (XmlException e) {
			GestLog.Error(this.getClass(), e);
		}
	}


	/**
	 * Ottiene un pannello con un 3D piechart
	 * 
	 * @param title
	 *            titolo
	 * @param dataset
	 *            dati
	 * @return il pannello
	 */
	public JPanel createMy3DPieChart(String title, PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, // title
				dataset, // PieDataset
				true, // create legend?
				true, // generate tooltips?
				false // generate URLs?
				);
		ChartPanel panel = new ChartPanel(chart);
		panel.setFillZoomRectangle(true);
		panel.setMouseWheelEnabled(true);
		return panel;
	}

	/**
	 * Ottiene un pannello con un barchart
	 * 
	 * @param title
	 *            titolo
	 * @param xlabel
	 *            titolo asse x
	 * @param ylabel
	 *            titolo asse y
	 * @param dataset
	 *            dati
	 * @return il pannello
	 */
	public JPanel createMyBarChart(String title, String xlabel, String ylabel,
			CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createBarChart(title, // title
				xlabel, // x-axis label
				ylabel, // y-axis label
				dataset, // data
				PlotOrientation.VERTICAL, false, // create legend?
				true, // generate tooltips?
				false // generate URLs?
				);

		ChartPanel panel = new ChartPanel(chart);
		panel.setFillZoomRectangle(true);
		panel.setMouseWheelEnabled(true);
		return panel;
	}

	/**
	 * Ottiene i dati relativi alle monete per metallo
	 * 
	 * @return i dati
	 * @throws XmlException
	 */
	private CategoryDataset getDatasetCoinByMetal() throws XmlException {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		TreeMap<String, Integer> valori = Statistiche.coinByMetal();
		for (String key : valori.keySet()) {
			dataset.addValue(valori.get(key), key, "metallo");
		}
		return dataset;
	}

	/**
	 * Ottiene i dati relativi alle monete per dimensione
	 * 
	 * @return i dati
	 * @throws XmlException
	 */
	private CategoryDataset getDatasetCoinBySize() throws XmlException {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		TreeMap<Double, Integer> valori = Statistiche.coinBySize();
		for (Double key : valori.keySet()) {
			dataset.addValue(valori.get(key), key, "mm");
		}
		return dataset;
	}

	/**
	 * Ottiene i dati relativi alle monete per anno
	 * 
	 * @return i dati
	 * @throws XmlException
	 */
	private CategoryDataset getDatasetCoinByYear() throws XmlException {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		TreeMap<String, Integer> valori = Statistiche.coinByYear();
		for (String key : valori.keySet()) {
			dataset.addValue(valori.get(key), key, "year");
		}
		return dataset;
	}

	/**
	 * Ottiene i dati relativi alle monete per metallo
	 * 
	 * @return i dati
	 * @throws XmlException
	 */
	private PieDataset getPieDatasetCoinByMetal() throws XmlException {
		DefaultPieDataset dataset = new DefaultPieDataset();
		TreeMap<String, Integer> valori = Statistiche.coinByMetal();
		for (String key : valori.keySet()) {
			dataset.setValue(key, valori.get(key));
		}
		return dataset;
	}
	
	private PieDataset getPieDatasetCoinByNominal() throws XmlException {
		DefaultPieDataset dataset = new DefaultPieDataset();
		TreeMap<String, Integer> valori = Statistiche.coinByNominal();
		for (String key : valori.keySet()) {
			dataset.setValue(key, valori.get(key));
		}
		return dataset;
	}
	

	/**
	 * inizializza i componenti grafici
	 */
	private void initComponents() {

		jToolBar1 = new javax.swing.JToolBar();
		jTabbedPane1 = new javax.swing.JTabbedPane();

		jToolBar1.setRollover(true);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE,
						400, Short.MAX_VALUE)
				.addComponent(jTabbedPane1,
						javax.swing.GroupLayout.DEFAULT_SIZE, 400,
						Short.MAX_VALUE));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jToolBar1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										25,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jTabbedPane1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										269, Short.MAX_VALUE)));
	}

	
}
